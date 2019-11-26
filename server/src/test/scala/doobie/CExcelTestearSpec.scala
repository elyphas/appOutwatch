package manik1.modules.generateorders

import monix.execution.Scheduler.Implicits.global
import cats.effect._
import cats.implicits._
import cats.data.EitherT

import file_maker.excel.CExcelTestear

import scala.concurrent.ExecutionContext.Implicits.global

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

class CExcelTestearSpec extends FlatSpec with Matchers with ScalaFutures {

  "reporteAlfonso" should "ser creado" in {

      val nameFile = "testear.xlsx"
      val path = "/home/elyphas/Documentos/" + nameFile

      val cexcel = new CExcelTestear()
      cexcel.create(path)

      assert(  1 > 0 )

  }

}