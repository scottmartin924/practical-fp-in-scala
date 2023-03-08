package example.conventions

import cats.effect.IOApp
import cats.effect.IO
import scala.util.Random
import concurrent.duration.DurationInt
import cats.effect.std.Semaphore
import cats.effect.std.Supervisor
import scala.util.control.NoStackTrace

object ConcurrencyManagement extends IOApp.Simple {

  case object NotFound extends NoStackTrace

  override def run: IO[Unit] = Supervisor[IO].use { sup =>
    // The 1 here is the number of permits the semaphore has
    Semaphore[IO](1).flatMap { sem =>
      sup.supervise(p1(sem).foreverM).void *>
        sup.supervise(p2(sem).foreverM).void *>
        IO.sleep(5.seconds).void
    }
  }

  def randomSleep: IO[Unit] = IO(Random.nextInt(1000)).flatMap { ms =>
    IO.sleep((ms + 700).millis)
  }.void

  def p1(sem: Semaphore[IO]): IO[Unit] =
    sem.permit.surround(IO.println("P1")) *> randomSleep

  def p2(sem: Semaphore[IO]): IO[Unit] =
    sem.permit.surround(IO.println("P2")) *> randomSleep
}
