package ir.mohsennavabi.externalservice.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

/**
  * Created by mohsennavabi on 2017/10/12AD.
  */
trait ExternalService extends Service {

  def countryList: ServiceCall[NotUsed, Countries]


  override def descriptor: Descriptor = {
    import Service._
    named("external-service")
      .withCalls(
        restCall(Method.GET, "/country/get/all", countryList _)
      )

      .withAutoAcl(false)
  }
}
