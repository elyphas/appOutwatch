package manik1.modules.generateorders

import monix.execution.Scheduler.Implicits.global

import scala.concurrent.ExecutionContext
import cats.effect._
import cats.implicits._
import cats.data.EitherT
import file_maker.excel.CExcelReporteGral
import doobie_db_catalogs.CReporte

import scala.concurrent.ExecutionContext.Implicits.global

/*import monix.execution.ExecutionModel.SynchronousExecution
import monix.execution.Scheduler
import monix.execution.schedulers.TrampolineScheduler*/

/*import scala.concurrent
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}*/

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

import file_maker.email.SendEmail

class CReporteAlfonsoDoobieSpec extends FlatSpec with Matchers with ScalaFutures {

  //implicit val scheduler = TrampolineScheduler(Scheduler.global, SynchronousExecution)

  "reporteAlfonso" should "ser creado" in {

      val report = new CReporte()

      val rows = report.run( 2019 ).unsafeRunSync()

      rows.foreach(println)

      val nameFile = "reporte.xlsx"
      val path = "/home/elyphas/Documentos/" + nameFile

      val cexcel = new CExcelReporteGral()
      cexcel.create(rows, path)

      val email = new SendEmail()
      /*email.SimpleEmailSender.send(
                  path,
        "Reporte.xlsx",
        "Envío reporte",
        "victor.aps@hotmail.es"
         )*/


    assert( rows.size > 0)

  }

}