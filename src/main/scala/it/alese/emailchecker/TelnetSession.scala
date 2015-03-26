package it.alese.emailchecker

import java.io.{InputStreamReader, BufferedReader, PrintWriter}
import java.net.Socket

case class TelnetSession(socket: Socket, input: PrintWriter, output: BufferedReader) {

  def test: Boolean = {
    val reply = Reply(output.readLine)
    reply.code match {
      case "554" => false
      case _ => true
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
  def apply(host: String): Either[Exception, TelnetSession] = {
    try {
      val socket = new Socket(host, 25)
      val session = new TelnetSession(
        socket,
        new PrintWriter(socket.getOutputStream, true),
        new BufferedReader(
          new InputStreamReader(socket.getInputStream)
        )
      )
      Right(session)
    } catch {
      case ex: Exception => Left(ex)
    }
  }
}
