package scalafuzz.internals

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import scalafuzz.Fuzzer.Target
import scalafuzz._

private[scalafuzz] class Runner[F[_]: Monad](loop: Loop[F, F], log: Log[F], reportAnalyzer: TargetRunReportAnalyzer[F]){

  /*
   todo:
  Initial stage:
- run target with an empty input;
- load inputs from Corpus;
- run each input (w/o mutations) through the target;
   */

  def program(options: FuzzerOptions, target: Target): F[FuzzerReport] = for {
    _ <- log.info(s"starting a run with options: $options")
    report <- loop.run(options, target, StreamedMutator.seedRandom(), reportAnalyzer)
    _ <- log.info(s"finished with results: $report")
  } yield report

}