package manik1.modules.generateorders

import monix.execution.Scheduler.Implicits.global

import scala.concurrent.ExecutionContext
import cats.effect._
import cats.implicits._
import cats.data.EitherT

import scala.concurrent.ExecutionContext.Implicits.global
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}
import doobie_db_catalogs.CFullTextSearch
import spatutorial.shared.typeclass.Show.show
import spatutorial.shared.typeclass.Show.ShowOps

class CFullTextSearchDoobieSpec extends FlatSpec with Matchers with ScalaFutures {

  "Full Text Search" should "ser importado" in {
      val fts = new CFullTextSearch()

      //val results = fts.run(Some("PAPEL BOND CARTA"))
      val results = fts.fts(Some("BOLIGRAFO AZUL 12"))

      val ver = results.unsafeRunSync()
      ver.foreach(println)

      assert( 0 == 0)

  }

}