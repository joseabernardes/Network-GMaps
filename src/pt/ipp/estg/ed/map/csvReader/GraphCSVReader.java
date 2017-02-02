/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.map.csvReader;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileInputStream;
import pt.ipp.estg.ed.map.mapPoint.Store;
import pt.ipp.estg.ed.adt.graph.weight.Distance;
import pt.ipp.estg.ed.adt.graph.LinkedNetwork;
import pt.ipp.estg.ed.adt.graph.NetworkADT;
import pt.ipp.estg.ed.adt.graph.weight.Time;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ipp.estg.ed.exceptions.InvalidIdException;
import pt.ipp.estg.ed.map.mapPoint.MapPoint;

/**
 * <h3>
 * ESTG - Escola Superior de Tecnologia e Gestão<br>
 * ED - Trabalho Pratico<br>
 * </h3>
 * <p>
 * <strong>Nome:</strong> José Paulo de Almeida Bernardes<br>
 * <strong>Número:</strong> 8150148<br>
 * <strong>Turma:</strong> LEI2T3<br>
 * </p>
 * <p>
 * <strong>Descrição: </strong><br>
 * Classe que representa a leitura de dados do csv
 * </p>
 */
public class GraphCSVReader {

    public static NetworkADT<MapPoint> readGraph(String vertexCSV, String edgesCSV) throws FileNotFoundException, IOException {
        NetworkADT<MapPoint> net = new LinkedNetwork<MapPoint>();
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(vertexCSV), StandardCharsets.ISO_8859_1), ',', '"', 1);

//                  CSVWriter vertex = new CSVWriter(new OutputStreamWriter(new FileOutputStream(vertexCSV),StandardCharsets.ISO_8859_1), ',', '"');
        String[] nextLine;
        Iterator<String[]> iterator = reader.iterator();
        //ler todos os vertices
        while (iterator.hasNext()) {
            nextLine = iterator.next();
            String[] columm = nextLine[0].split(";");
            net.addVertex(new Store(columm[0], Integer.parseInt(columm[1]), Double.parseDouble(columm[2]), Double.parseDouble(columm[3])));
        }
        //ler todas as arestas
        reader = new CSVReader(new InputStreamReader(new FileInputStream(edgesCSV), StandardCharsets.ISO_8859_1), ',', '"', 1);

        iterator = reader.iterator();

        while (iterator.hasNext()) {
            nextLine = iterator.next();
            if (nextLine != null) {
                String[] columm = nextLine[0].split(";");
                try {
                    net.addEdge(new Store(Integer.parseInt(columm[0])), new Store(Integer.parseInt(columm[1])), new Distance(Double.parseDouble(columm[2])), new Time(Double.parseDouble(columm[3])));
                } catch (InvalidIdException ex) {
                    Logger.getLogger(GraphCSVReader.class.getName()).log(Level.SEVERE, "ID1=" + columm[0] + " ID2=" + columm[1] + " ID não existe!", ex);
                }

            }
        }

        return net;
    }

    public static void writeGraph(String vertexCSV, String edgeCSV, NetworkADT<MapPoint> net) throws FileNotFoundException, IOException {
//        CSVWriter vertex = new CSVWriter(new FileWriter(vertexCSV), ';');
        CSVWriter edge = new CSVWriter(new OutputStreamWriter(new FileOutputStream(edgeCSV), StandardCharsets.ISO_8859_1), ';', CSVWriter.NO_QUOTE_CHARACTER, "\n");

//        CSVWriter vertex = new CSVWriter(new OutputStreamWriter(new FileOutputStream(vertexCSV), StandardCharsets.UTF_16LE));
        CSVWriter vertex = new CSVWriter(new OutputStreamWriter(new FileOutputStream(vertexCSV), StandardCharsets.ISO_8859_1), ';', CSVWriter.NO_QUOTE_CHARACTER, "\n");

        Iterator<MapPoint> iterator = net.iterator();
        String[] vertexHead = {"NOME", "ID", "LATITUDE", "LONGITUDE"};
        String[] edgeHead = {"ORIGEM", "DESTINO", "DISTANCIA", "TEMPO"};
        vertex.writeNext(vertexHead);
        edge.writeNext(edgeHead);

        while (iterator.hasNext()) {
            MapPoint next = iterator.next();
            String[] nextLine = {next.getTitle(), Integer.toString(next.getId()), Double.toString(next.getLatitude()), Double.toString(next.getLongitude())};
            vertex.writeNext(nextLine);
            Iterator<MapPoint> edges = net.iteratorAllEdges(next);
            while (edges.hasNext()) {
                MapPoint destiny = edges.next();
                String[] nextL = {Integer.toString(next.getId()), Integer.toString(destiny.getId()), Double.toString(net.getWeights(next, destiny, new Distance())), Double.toString(net.getWeights(next, destiny, new Time()))};
                edge.writeNext(nextL);

            }
        }
        vertex.close();
        edge.close();
    }

}
