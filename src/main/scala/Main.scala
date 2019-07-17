import java.time.{Instant, OffsetDateTime}

import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Main extends App {

  case class Entity(id: Long, instant: Instant)

  val dummyId     = 100
  val dummyTime   = "2019-07-17T12:34:00+03:00"
  val dummyEntity = Entity(dummyId, OffsetDateTime.parse(dummyTime).toInstant)

  class Entities(tag: Tag) extends Table[Entity](tag, "ENTITIES") {
    def id = column[Long]("ID", O.PrimaryKey)
    def instant = column[Instant]("INSTANT")
    override def * = (id, instant) <> (Entity.tupled, Entity.unapply)
  }
  val entities = TableQuery[Entities]

  // Database is configured in application.conf
  val db = Database.forConfig("db")

  val insertAndGet = for {
    _ <- entities.schema.create
//    _ <- entities += dummyEntity
    _ <- sqlu"insert into entities (id, instant) values (100, '2019-07-17T12:34:00+03:00')"
    e <- entities.result.headOption
  } yield e

  val result = Await.result(db.run(insertAndGet), Duration.Inf)
  assert { result.get.instant == dummyEntity.instant }
}
