package $package$

import com.typesafe.scalalogging.LazyLogging

object HelloWorld extends App with LazyLogging {

  logger.info("Hello world.")
}
