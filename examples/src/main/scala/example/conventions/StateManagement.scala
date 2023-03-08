package example.conventions

import cats.effect.kernel.Ref
import cats.Functor
import cats.syntax.all._
import cats.effect.IOApp
import cats.effect.IO

object StateManagement extends IOApp.Simple {

  // Test the counter for funsies
  override def run: IO[Unit] = for {
    cntr <- Counter.make[IO]
    _ <- cntr.increment
    _ <- cntr.increment
    _ <- cntr.get.flatMap(IO.println)
    _ <- cntr.decrement
    _ <- cntr.get.flatMap(IO.println)
  } yield ()

  trait Counter[F[_]] {
    def increment: F[Unit]
    def decrement: F[Unit]
    def get: F[Int]
  }

  // I can't decide if I prefer having implementation in this companion or in separate class?
  object Counter {

    def make[F[_]: Functor: Ref.Make]: F[Counter[F]] = {
      Ref.of[F, Int](0).map { ref =>
        new Counter[F] {
          def increment: F[Unit] = ref.update(_ + 1)
          def decrement: F[Unit] = ref.update(_ - 1)
          def get: F[Int] = ref.get
        }
      }
    }
  }

}
