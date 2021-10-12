# Nanthealth Kubernetes Utilities
[![Kotlin](https://img.shields.io/badge/kotlin-1.4.0-orange.svg?logo=kotlin)](http://kotlinlang.org)

Kotlin Multiplatform using Quarkus as the backend. It main purpose is to demonstrate the use of [Kotlin](https://kotlinlang.org/) :two_hearts: for a full stack application that uses [Quarkus](https://quarkus.io/) :two_hearts: as a backend.   

On the frontend the application is using Kotlin react wrappers and a library [Kotlin - React Material Design Web Components](https://gitlab.com/zsinz-kotlin/kotlin-react-mdwc) for components. The application
itself basically displays a list of all the available components.


# How to use it

## Dev Mode

Here the notion is to utilize the automatic hot deploy capabilities of both **Kotlin Javascript** and **Quarkus**
* Backend: Start the quarkus server   
```gradlew :quarkusDev```
* Frontend: Start the node DevServer   
  ```gradlew :jsBrowserDevelopmentRun```
>Note: the default pool for the quarkus server had to change to 8081 to allow this. The original option was to change the port
>on the devServer in gradle, but this did not work do to a bug/limitation on the Kotlin Gradle Multiplatform plugin

## Production 
```gradlew :quarkusBuild``` Simply!

### Docker - JVM
#### Build
```docker build -t nanthealth/nanthealth-kubernetes-utilities:jvm -f  ./src/jvmMain/docker/Dockerfile.multistage.jvm .```
#### Run
```docker run -dit --name web -p 8080:8080 nanthealth/nanthealth-kubernetes-utilities:jvm```

### Docker - Native
#### Build
```docker build -t nanthealth/nanthealth-kubernetes-utilities:native -f  ./src/jvmMain/docker/Dockerfile.multistage.native .```
#### Run
```docker run -dit --name web -p 8080:8080 nanthealth/nanthealth-kubernetes-utilities:native``


## DTE

### Deploying to the DTE
Deploying the application to the cluster via the helm chart is straightforward. The main value that is set per environment is the
CA bundle that is required for the web hook 

```shell script
helm upgrade --install --force --namespace nanthealth-internal nanthealth-kubernetes-utilities  nanthealth-feature/nanthealth-kubernetes-utilities --version=0.0.1 --set global.ingress.internal.hosts\[0\].alias='ADMINISTRATION-INTERNAL-VIP',global.ingress.internal.hosts\[0\].name=dtkt1.qa.navinet.local
helm upgrade --install --force  nanthealth-kubernetes-utilities  ./helm/nanthealth-kubernetes-utilities  --set global.ingress.internal.hosts\[0\].alias='ADMINISTRATION-INTERNAL-VIP',global.ingress.internal.hosts\[0\].name=dtkt1.qa.navinet.local --debug --dry-run
run

```
>  If you are using zsh, then you need to escape square brackets like \[0\].

### Example of testing

```yaml
cat <<EOF | kubectl apply -f -
apiVersion: bitnami.com/v1alpha1
kind: SealedSecret
metadata:
  annotations:
    helm.sh/hook-weight: "1"
  name: acs-access-control-service-secrets-test
  namespace: default
spec:
  encryptedData:
    appsettings.secrets.json: <generated value>
EOF
```


## Kotlin Multiplatform using Quarkus
<!--- If your README is long or you have some specific process or steps you want contributors to follow, consider creating a separate CONTRIBUTING.md file--->
To contribute to kotlin-multiplatform-quarkus, follow these steps:

1. Fork this repository.
2. Create a branch: `git checkout -b <branch_name>`.
3. Make your changes and commit them: `git commit -m '<commit_message>'`
4. Push to the branch: `git push
5. Create a merge request

Alternatively see the GitLab documentation on [creating a merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html).
