package it.alese.emailchecker

import java.io.{InputStreamReader, BufferedReader, PrintWriter}
import java.net.Socket

case class TelnetSession(socket: Socket, input: PrintWriter, output: BufferedReader) {

  def allowsConnections: Boolean = {
    Reply(output.readLine).code match {
      case "220" => true
      case _ => false
    }
  }

  def send(command: String): String = {
    input.println(command)
    output.readLine
  }

  def close(): Unit = {
    send("quit")
    socket.close()
  }
}

object TelnetSession {
  def apply(host: String): TelnetSession = {
      val socket = new Socket(host, 25)
      new TelnetSession(
        socket,
        new PrintWriter(socket.getOutputStream, true),
        new BufferedReader(
          new InputStreamReader(socket.getInputStream)
        )
      )
  }
}
