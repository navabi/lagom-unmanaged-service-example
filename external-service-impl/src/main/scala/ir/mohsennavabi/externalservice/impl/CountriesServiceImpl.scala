package ir.mohsennavabi.externalservice.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import ir.mohsennavabi.externalservice.api.{Countries, CountriesService, Country, ExternalService}
import play.api.libs.json.{JsObject, JsValue, Json}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by mohsennavabi on 2017/10/12AD.
  */
class CountriesServiceImpl(externalService: ExternalService) extends CountriesService {

  override def countryList: ServiceCall[NotUsed, List[JsObject]] = ServiceCall {
    request =>
      val result = externalService.countryList.invoke()
      result.map(response =>
        response.RestResponse.result.map{
          country=>
            Json.obj(
              "name"->country.name,
              "code"->country.alpha3_code
            )
        }
      )
  }
}
