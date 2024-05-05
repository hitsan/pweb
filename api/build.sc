import mill._, scalalib._
import $ivy.`io.github.alexarchambault.mill::mill-native-image::0.1.26`
import io.github.alexarchambault.millnativeimage.NativeImage

object api extends ScalaModule with NativeImage {
  def scalaVersion = "3.4.0"
  def ivyDeps = Agg(
    ivy"dev.zio::zio:2.1-RC1",
    ivy"dev.zio::zio-http:3.0.0-RC6",
    ivy"com.google.api-client:google-api-client:2.0.0",
    ivy"com.google.oauth-client:google-oauth-client-jetty:1.34.1",
    ivy"com.google.apis:google-api-services-calendar:v3-rev20220715-2.0.0"
  )
  def nativeImageName         = "api"
  def nativeImageMainClass    = "Main"
  def nativeImageClassPath    = runClasspath()
  def nativeImageGraalVmJvmId = "graalvm-java17:22.3.1"
  def nativeImageOptions = Seq(
    "--no-fallback",
    "--enable-url-protocols=http,https",
    "-Djdk.http.auth.tunneling.disabledSchemes=",
  ) ++ (if (sys.props.get("os.name").contains("Linux")) Seq("--static") else Seq.empty)
}