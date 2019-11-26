package manik1.modules.generateorders

import monix.execution.Scheduler.Implicits.global

import scala.concurrent.ExecutionContext
import cats.effect._
import cats.implicits._
import cats.data.EitherT

import scala.concurrent.ExecutionContext.Implicits.global

/*import monix.execution.ExecutionModel.SynchronousExecution
import monix.execution.Scheduler
import monix.execution.schedulers.TrampolineScheduler*/

/*import scala.concurrent
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}*/

import  doobie_db_catalogs.CComparativoDoobie

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

import doobie_db_catalogs.CImportComparativo

import spatutorial.shared.typeclass.Show.show
import spatutorial.shared.typeclass.Show.ShowOps

class CImportComparativeDoobieSpec extends FlatSpec with Matchers with ScalaFutures {

  "import comparativo" should "ser importado" in {
    val importCompara = CImportComparativo.loadCSV("/home/elyphas/Descargas/comparativo.csv")

    println(importCompara)

    //val ver = importCompara.lo

    assert( 0 == 0)

  }

}