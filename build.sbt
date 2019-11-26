import sbtcrossproject.{CrossType, crossProject}
import com.typesafe.sbt.less.Import.LessKeys

/**Resolving a snapshot version. It's going to be slow unless you use `updateOptions := updateOptions.value.withLatestSnapshots(false)` options* */
updateOptions := updateOptions.value.withLatestSnapshots(false)

lazy val server = (project in file("server"))
  .settings(commonSettings)
  .settings(
    scalacOptions ++= Seq("-Ypartial-unification"),
    scalacOptions in ThisBuild ++= Seq("-Ypartial-unification"),
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    LessKeys.compress in Assets := true,
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    //pipelineStages := Seq(digest, gzip),
    resolvers += "jitpack" at "https://jitpack.io",
    libraryDependencies ++= Seq (
      "org.scalatest" %%% "scalatest" % "3.0.8" % Test,
      "com.typesafe.akka" %% "akka-http" % "10.1.9",
      "com.typesafe.akka" %% "akka-stream" % "2.5.25",
      "com.vmunier" %% "scalajs-scripts" % "1.1.2",
    ),
    WebKeys.packagePrefix in Assets := "public/",
    //sourceDirectories in (Compile, TwirlKeys.compileTemplates) := (unmanagedSourceDirectories in Compile).value,
    managedClasspath in Runtime += (packageBin in Assets).value,
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0-M4"),
    javaOptions in run += "-Xms4G -Xmx8G",    //-XX:MaxPermSize=1024M,
    // When running tests, we use this configuration
    javaOptions in Test += s"-Dconfig.file=${sourceDirectory.value}/test/resources/application.test.conf",
    // We need to fork a JVM process when testing so the Java options above are applied
    fork in Test := true,
  )
  .enablePlugins(SbtWeb, SbtTwirl, WebScalaJSBundlerPlugin, JavaAppPackaging)
  .dependsOn(sharedJvm)

lazy val client = (project in file("client"))
  .settings(commonSettings)
  .settings(
    /*npmDependencies in Compile ++= Seq(
      "snabbdom" -> "0.7.3",
    ),*/
    /*npmDevDependencies in Compile ++= Seq(
      "webpack-merge" -> "4.1.2",
      "snabbdom" -> "0.7.3",
      "imports-loader" -> "0.8.0",
      "expose-loader" -> "0.7.5"
    ),*/
    version in webpack := "4.41.2",
    //version in startWebpackDevServer := "3.9.0",
    //webpackDevServerPort := 8080,
    requireJsDomEnv in Test := true,
    scalaJSUseMainModuleInitializer := true,
    //scalaJSUseMainModuleInitializer in Test := false,
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)), // configure Scala.js to emit a JavaScript module instead of a top-level script

    //webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly(),

    useYarn := true,
    version in installJsdom := "15.2.1",
    scalacOptions ++= Seq("-P:scalajs:sjsDefinedByDefault", "-Ypartial-unification"),
    //webpackBundlingMode := BundlingMode.Application,
    //The baseDirectory is "tsoolik/client/"
    //webpackConfigFile in fastOptJS := Some(baseDirectory.value / "dev.webpack.config.js"),
    webpackConfigFile in fastOptJS := Some(baseDirectory.value / "my.custom.webpack.config.js"), // siguiendo ejemplo de outwatch.
    //webpackConfigFile in Test := Some(baseDirectory.value / "test.webpack.config.js"),//original
    //webpackConfigFile in Test := Some((baseDirectory in ThisBuild).value / "test.webpack.config.js"),
    //jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv(), //@sjrd; If you use CommonJS modules, don't use JSDOMNodeJSEnv.
    //npmDevDependencies in Compile += "expose-loader" -> "0.7.5",
    //skip in packageJSDependencies := false,
    resolvers += "jitpack" at "https://jitpack.io",
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % "3.0.8" % Test,
      "com.github.cornerman.outwatch" %%% "outwatch" % "0a470538",
      "com.github.cornerman.outwatch" %%% "outwatch-monix" % "0a470538",
    ),
    emitSourceMaps := false,
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0-M4"),
  )
  .enablePlugins( ScalaJSPlugin,/* ScalaJSWeb,*/ ScalaJSBundlerPlugin)
  .dependsOn(sharedJs)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType ( CrossType.Pure )
  .in(file ( "shared" ) )
  .settings ( commonSettings )
  .settings (
    resolvers += "jitpack" at "https://jitpack.io",
    scalacOptions ++= Seq(
      "-Ypartial-unification",
      "-encoding",
      "UTF-8",
      "-unchecked",
      "-deprecation",
      "-explaintypes",
      "-feature",
      "-language:_",
      "-Xlint",
      "-Xlint:adapted-args",
      //"-Wextra-implicit",
      "-Xlint:infer-any",
      //"-Wvalue-discard",
      "-Xlint:nullary-override",
      "-Xlint:nullary-unit"
    ),
    scalacOptions in ThisBuild ++= Seq("-Ypartial-unification"),
    libraryDependencies ++= Seq (
      "org.scalatest" %%% "scalatest" % "3.0.8" % Test,
      "io.suzaku" %%% "boopickle" % "1.3.1",  //"1.2.6",
      "org.typelevel" %%% "cats-effect" % "2.0.0",
      "org.typelevel" %% "cats-core" % "2.0.0",
      "com.typesafe.akka" %% "akka-actor" % "2.5.25",

      "com.github.cornerman.covenant" %%% "covenant-http" % "master-SNAPSHOT",
      "com.github.cornerman.covenant" %%% "covenant-ws" % "master-SNAPSHOT"
    )
  )
  .jsConfigure(_ enablePlugins ScalaJSWeb)
  .jsConfigure(_ enablePlugins ScalaJSBundlerPlugin)
  .jsConfigure(_ enablePlugins SbtWeb)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.12.6",
  organization := "tsoolik" /**explicar, desarrollar*/      //https://es.freelang.net/enlinea/maya.php?lg=es
)

// loads the server project at sbt startup
onLoad in Global := ( onLoad in Global ).value andThen { s: State => "project server" :: s }