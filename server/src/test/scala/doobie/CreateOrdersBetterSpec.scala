package manik1.modules.generateorders

import cats.data.EitherT
import monix.execution.Scheduler.Implicits.global
import scala.concurrent.ExecutionContext
//import cats.effect.IO
import cats.effect._
import cats.data.EitherT
import cats.implicits._

import manik1.manik1.shared.BuscaPedidoXProveedor
import scala.concurrent.ExecutionContext.Implicits.global
import monix.execution.ExecutionModel.SynchronousExecution
import monix.execution.Scheduler
import monix.execution.schedulers.TrampolineScheduler
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}
import doobie_db_catalogs.{CDetallePedido, CDmlQuerys, CGralDataPedido, CGralDataProceso}
import shapeless.MkGenericLens

import spatutorial.shared.{DetallePedido, Fechas, GralDataPedido, GralDataProceso}

import scala.concurrent
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

import doobie_db_catalogs.CFallo

import manik1.manik1.shared.{Calcular, BuscaPedidoXProveedor, CantidadXDividir}
import spatutorial.shared.typeclass.Show._

class CreateOrdersBetterSpec extends FlatSpec with Matchers with ScalaFutures {
  /*implicit val scheduler = TrampolineScheduler(Scheduler.global, SynchronousExecution)

  import slick.jdbc.PostgresProfile
  val jdbcProfile = PostgresProfile
  import jdbcProfile.api._

  implicit val getCantidadXDivirResult = GetResult(r => CantidadXDividir(r.nextString, r.nextString, r.nextString, r.nextInt, r.nextInt, r.nextInt, r.nextDouble, r.nextInt, r.nextInt, r.nextInt))

  implicit val getBuscaPedidoXProveedorResult = GetResult(r => BuscaPedidoXProveedor(
    r.nextString, r.nextString, r.nextString, r.nextString, r.nextInt, r.nextDouble, r.nextString, r.nextString,
    r.nextString, r.nextInt, r.nextInt, r.nextInt, r.nextInt, r.nextString, r.nextInt ))

  val fallo = new CFallo()
  val generateOrders = new CDmlQuerys()

  "at generateOrders" should "mostrar sus resultados" in {
    val idComparative = "4-2018-ADQ2"

    val lstOrdersF = fallo.calcularCantidades(idComparative)
    val dataGralesProcess = new CGralDataProceso()
    val datGralesProcess = dataGralesProcess.byId(idComparative)

    val calcOrdersToGenerate: EitherT[Future, String, Seq[GralDataPedido]] = for {
      gralData <- EitherT(datGralesProcess)
      lstOrders <- EitherT(lstOrdersF)
      lastOrder <- EitherT(generateOrders.run(sql"""select max(no_pedido) as ultimo from tblpedi where compra = 'PEDI' and ejercicio = ${gralData.ejercicio}""".as[String]))
    } yield {
      var numOrder: Int = lastOrder.head.toInt
      lstOrders.map { provGanad =>
        numOrder += 1
        fallo.createGralDataPedidos(gralData, provGanad, numOrder)
      }
    }

    /*************************************************************************************/
    //import scala.concurrent.ExecutionContext
    //implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)
    //val finalResult = IO.fromFuture(IO.delay(calcOrdersToGenerate.value))
    /**************************************************************************************/

    val calc = calcOrdersToGenerate
    val ordersCreated = for {
      orders <- calc
      v <- EitherT(new CGralDataPedido().insertMany(orders))
    } yield v

    val buscaCantXdiv = fallo.searchForCantXdiv(idComparative)
    println("Changos @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 1")
    /**********************       Ordenes creadas              *******************************/
    val generateDetails = for {
      ordCreated <- ordersCreated
      cantXDividir <- EitherT(buscaCantXdiv)
    } yield {
      println("Changos @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 2")
      val insertarClaves = cantXDividir.map { cantXDiv =>
        val qrySQL = fallo.sqlBuscaPedProv( cantXDiv.id_comparativo, cantXDiv.cve_articulo, cantXDiv.programa, cantXDiv.renglon, cantXDiv.entrega)
        val numPedidoProveedor = generateOrders.run(qrySQL.as[BuscaPedidoXProveedor])
        for {numPedProv <- EitherT(numPedidoProveedor) } yield fallo.createDetailsOrder(numPedProv)
      }.head
      for { createDetails <- insertarClaves } yield {
        val detPedido = new CDetallePedido()
        detPedido.insertMany(createDetails)
      }
      ordCreated
    }

    val ver = Await.result(generateDetails.value, 50 seconds)

    println("#################################################################")
    ver match {
      case Right(value) => value.map( i => println(show(i)))
      case Left(value) => println(s"EEError   $value")
    }
    assert(0 != 0 )
  }*/

}