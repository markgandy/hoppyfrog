package com.hoppyfrog

import com.mongodb.casbah.MongoClient
import com.novus.salat._
import com.novus.salat.global._
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeExample
import spray.http.StatusCodes._
import spray.routing.HttpService
import spray.testkit.Specs2RouteTest

class VenueServiceSpec extends Specification with Specs2RouteTest with HttpService with VenueService with BeforeExample {

  val client = MongoClient("localhost", 27017)
  val db = client("admin")
  val collection = db("venues")
  val uri = "/venues"
  val venueId = 1

  def actorRefFactory = system
  def before = db.dropDatabase()

  sequential

  s"GET $uri/$venueId" should {

    val expected = Venue(venueId, "The Cornish Arms")

    "return OK" in {
      insertVenue(expected)
      Get(s"$uri/$venueId") ~> myRoute ~> check {
        response.status must equalTo(OK)
      }
    }

    def insertVenue(venue: Venue) {
      collection.insert(grater[Venue].asDBObject(venue))
    }
  }

}
