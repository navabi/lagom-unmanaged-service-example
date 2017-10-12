package ir.mohsennavabi.externalservice.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{JsObject, JsValue}

/**
  * Created by mohsennavabi on 2017/10/12AD.
  */
trait CountriesService extends Service {


  def countryList: ServiceCall[NotUsed, List[JsObject]]

  override def descriptor: Descriptor = {
    import Service._
    named("countries-service")
      .withCalls(
        restCall(Method.GET, "/api/country-list", countryList _)

      )
      .withAutoAcl(true)
  }
}

