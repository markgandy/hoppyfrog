package com.hoppyfrog

import akka.actor.Actor
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.MongoClient
import com.novus.salat._
import com.novus.salat.global._
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol
import spray.routing._

case class Venue(_id: Int, name: String)

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class VenueServiceActor extends Actor with VenueService {

  val config = context.system.settings.config
  val client = MongoClient(config.getString("books.db.host"), config.getInt("books.db.port"))
  val db = client(config.getString("books.db.db"))
  val collection = db(config.getString("books.db.collection"))

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait VenueService extends HttpService with DefaultJsonProtocol {

  implicit val venuesFormat = jsonFormat2(Venue)
  val collection: MongoCollection

  val myRoute = {
    path("venues" / IntNumber) { venueId =>
      get {
        complete(
          grater[Venue].asObject(
            collection.findOne(MongoDBObject("_id" -> venueId)).get
          )
        )
      }
    }
  }
}