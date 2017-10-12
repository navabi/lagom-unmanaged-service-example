package ir.mohsennavabi.hello.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import ir.mohsennavabi.hello
import ir.mohsennavabi.hello.api.HelloService

/**
  * Implementation of the HelloService.
  */
class HelloServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends HelloService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the LagomUnmanagedServices-Example entity for the given ID.
    val ref = persistentEntityRegistry.refFor[HelloEntry](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the LagomUnmanagedServices-Example entity for the given ID.
    val ref = persistentEntityRegistry.refFor[HelloEntry](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }


  override def greetingsTopic(): Topic[ ir.mohsennavabi.hello.api.GreetingMessageChanged] =
    TopicProducer.singleStreamWithOffset {
      fromOffset =>
        persistentEntityRegistry.eventStream(HelloEvent.Tag, fromOffset)
          .map(ev => (convertEvent(ev), ev.offset))
    }

  private def convertEvent(helloEvent: EventStreamElement[HelloEvent]):  ir.mohsennavabi.hello.api.GreetingMessageChanged = {
    helloEvent.event match {
      case GreetingMessageChanged(msg) => ir.mohsennavabi.hello.api.GreetingMessageChanged(helloEvent.entityId, msg)
    }
  }
}
