QUAY_NAMESPACE = ricardozanini
VERSION = 1.0.0
JAVA_VERSION = ${VERSION}-SNAPSHOT

# Image Reference docs:
# https://access.redhat.com/documentation/en-us/red_hat_jboss_middleware_for_openshift/3/html-single/red_hat_java_s2i_for_openshift/index#configuration_environment_variables
.PHONY: build-images
build-images:
	- make build-weather-image
	- make build-rain-image

.PHONY: build-weather-image
build-weather-image:
	@echo .......... Building Weather API Gateway Image .................
	s2i build . openjdk/openjdk-11-rhel8 quay.io/${QUAY_NAMESPACE}/weather-api-gateway:${VERSION} -e MAVEN_ARGS_APPEND="-pl weather-api-gateway -am" -e ARTIFACT_DIR="weather-api-gateway/target" -e JAVA_APP_JAR="weather-api-gateway-${JAVA_VERSION}-runner.jar"
	@echo .......... Pushing to quay.io .................................
	docker tag quay.io/${QUAY_NAMESPACE}/weather-api-gateway:${VERSION} quay.io/${QUAY_NAMESPACE}/weather-api-gateway:latest
	docker push quay.io/${QUAY_NAMESPACE}/weather-api-gateway:${VERSION}
	docker push quay.io/${QUAY_NAMESPACE}/weather-api-gateway:latest
	@echo .......... Image Weather API Gateway successfully built .......

.PHONY: build-rain-image
build-rain-image:
	@echo .......... Building Rain Forecast Process Image ...............
	s2i build . openjdk/openjdk-11-rhel8 quay.io/${QUAY_NAMESPACE}/rain-forecast-process:${VERSION}  -e MAVEN_ARGS_APPEND="-pl rain-forecast-process -am" -e ARTIFACT_DIR="rain-forecast-process/target" -e JAVA_APP_JAR="rain-forecast-process-${JAVA_VERSION}-runner.jar"
	@echo .......... Pushing to quay.io .................................
	docker tag quay.io/${QUAY_NAMESPACE}/rain-forecast-process:${VERSION} quay.io/${QUAY_NAMESPACE}/rain-forecast-process:latest
	docker push quay.io/${QUAY_NAMESPACE}/rain-forecast-process:${VERSION}
	docker push quay.io/${QUAY_NAMESPACE}/rain-forecast-process:latest
	@echo .......... Image Rain Forecast Process successfully built .....

