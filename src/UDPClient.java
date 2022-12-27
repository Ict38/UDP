import model.Answer;
import model.Student;

import java.io.*;
import java.net.*;

public class UDPClient{
    DatagramSocket socket = null;
    int port = 11310;

    public void connection(){
            try{
                socket = new DatagramSocket();
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }

    }

    public void send(Student student){
        if(socket != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(baos);
                out.writeObject(student);
                byte[] oo = baos.toByteArray();

                InetAddress address = InetAddress.getByName("192.168.0.103");
                DatagramPacket sendPacket = new DatagramPacket(oo, oo.length, address, port);
                socket.send(sendPacket);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Answer receiver(){
        if(socket != null) {
            byte[] receiveData = new byte[10000];
            try {
                // Nhan du lieu tu server gui ve
                DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
                socket.receive(receivePacket);
                // Chuyen du lieu ve dang byte
                byte[] data = receivePacket.getData();

                // Chuyen du lieu tu dang byte ve dang Object
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                Answer res = (Answer) ois.readObject();
                return res;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return  null;
    }

    public static void main(String[] args) {
        Student student = new Student("B19DCCN304","CHU DUC HUY","192.168.0.103",6);
        UDPClient client = new UDPClient();
        client.connection();
        client.send(student);
        System.out.println(client.receiver());
    }
}