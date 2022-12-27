import model.Answer;
import model.Student;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    DatagramSocket serverSocket = null;
    int port = 11310;

    public void openServer(){
        try{
            serverSocket = new DatagramSocket(port);
        } catch (SocketException exception){
            System.out.println(exception);
        }
    }

    public void listening(){
        int sendLengthData;
        while (true){
            try{
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
                serverSocket.receive(receivePacket);
                byte[] input = receivePacket.getData();

                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(input));
                Student student = (Student) ois.readObject();

                System.out.println(student);

                Answer answer = new Answer(student);

                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(baos);
                out.writeObject(answer);
                byte[] oo = baos.toByteArray();

                sendLengthData = oo.length;

                DatagramPacket sendPacket = new DatagramPacket(oo, oo.length, IPAddress, port);

//                DatagramPacket sendPacket = new DatagramPacket(ret.getBytes(), ret.length(), IPAddress, port);
                serverSocket.send(sendPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        UDPServer server = new UDPServer();
        server.openServer();
        server.listening();
    }
}
