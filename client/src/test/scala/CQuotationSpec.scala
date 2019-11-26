package tettestear

import outwatch.dom._
import outwatch.dom.dsl._
import cats.effect.IO

import org.scalajs.dom._

//class CQuotationSpec extends FlatSpec with Matchers with ScalaFutures {
class DomEventSpec extends JSDomSpec  {

  "txt in frmComparative" should "contain a string" in {
      val message = "Hello World!"

      val ver = div( label( id:="lblFolio"), input( id := "txtFolio", "ver" ) )

      OutWatch.renderInto[ IO ]("#root", ver ).unsafeRunSync( )


      val existe = document.getElementById("txtFolio")

      println("Vamos a imprimir la etiqueta    !!!!!!   ")
      println ( existe.textContent )

      document.body.innerHTML.contains(message) shouldBe true

    }

}