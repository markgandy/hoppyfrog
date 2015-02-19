package com.hoppyfrog

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._

class VenueServiceSpec extends Specification with Specs2RouteTest with VenueService {
  def actorRefFactory = system
  
  "VenuesService" should {

    "return a venue for GET requests to /venue/1" in {
      Get("/venues/1") ~> myRoute ~> check {
        responseAs[String] must contain("venue")
      }
    }

    "leave GET requests to other paths unhandled" in {
      Get("/kermit") ~> myRoute ~> check {
        handled must beFalse
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      Put("/venues/1") ~> sealRoute(myRoute) ~> check {
        status === MethodNotAllowed
        responseAs[String] === "HTTP method not allowed, supported methods: GET"
      }
    }
  }
}
