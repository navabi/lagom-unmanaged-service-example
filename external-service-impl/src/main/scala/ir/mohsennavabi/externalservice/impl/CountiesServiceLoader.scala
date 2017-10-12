package ir.mohsennavabi.externalservice.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}
import com.softwaremill.macwire.wire
import ir.mohsennavabi.externalservice.api.{ExternalService, CountriesService}
import play.api.libs.ws.ahc.AhcWSComponents

/**
  * Created by mohsennavabi on 2017/10/12AD.
  */
class CountriesServiceLoader  extends LagomApplicationLoader{
  override def load(context: LagomApplicationContext): LagomApplication =
    new CountriesServiceApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new CountriesServiceApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[CountriesService])
}

abstract class CountriesServiceApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[CountriesService](wire[CountriesServiceImpl])

  lazy val externalService=serviceClient.implement[ExternalService]
}
