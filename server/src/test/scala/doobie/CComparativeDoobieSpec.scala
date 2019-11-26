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

import doobie_db_catalogs.CComparativoDoobie
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

import spatutorial.shared.typeclass.Show.show
import spatutorial.shared.typeclass.Show.ShowOps

class CComparativeSpec extends FlatSpec with Matchers with ScalaFutures {

  "comparativo" should "ser creado" in {

    val compara = new CComparativoDoobie()

    /*
    val rows = compara.run("1448-2019-ADQ2").unsafeRunSync()
    val retensionISR = rows.map(r =>(r.retension_isr.toDouble/100) * r.minimo).reduce(_+_)
    println(s"La retension del isr es: $retensionISR")
    */

    //assert( rows.size > 0)

  }

}