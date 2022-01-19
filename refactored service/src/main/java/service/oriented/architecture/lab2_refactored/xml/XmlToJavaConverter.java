package service.oriented.architecture.lab2_refactored.xml;



import service.oriented.architecture.lab2_refactored.dto.AlbumDTO;
import service.oriented.architecture.lab2_refactored.dto.CoordinatesDTO;
import service.oriented.architecture.lab2_refactored.dto.MusicBandDTO;
import service.oriented.architecture.lab2_refactored.dto.dtoList.AlbumDTOList;
import service.oriented.architecture.lab2_refactored.dto.dtoList.CoordinatesDTOList;
import service.oriented.architecture.lab2_refactored.dto.dtoList.MusicBandDTOList;
import service.oriented.architecture.lab2_refactored.exceptions.XmlParseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class XmlToJavaConverter {

    public CoordinatesDTOList getCoordinatesDTOFromXml(String xml) {
        CoordinatesDTOList coordinatesDTOList = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CoordinatesDTOList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            coordinatesDTOList = (CoordinatesDTOList) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return coordinatesDTOList;
    }

    public CoordinatesDTO getSingleCoordinatesDTOFromXml(String xml) {
        CoordinatesDTO coordinatesDTO = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CoordinatesDTO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            coordinatesDTO = (CoordinatesDTO) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return coordinatesDTO;
    }


    public MusicBandDTOList getMusicBandDTOFromXml(String xml) {
        MusicBandDTOList musicBandDTOList;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MusicBandDTOList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            musicBandDTOList = (MusicBandDTOList) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (JAXBException e) {
            throw new XmlParseException(e.getMessage());
        }
        System.out.println(xml);
        System.out.println("21-84u2usugnsdfg");
        System.out.println(musicBandDTOList.getMusicBandList());
        System.out.println(musicBandDTOList.getMusicBandList().get(0));
        return musicBandDTOList;
    }

    public MusicBandDTO getSingleMusicBandDTOFromXml(String xml) {
        MusicBandDTO musicBandDTO;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MusicBandDTO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            musicBandDTO = (MusicBandDTO) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (JAXBException e) {
            throw new XmlParseException(e.getMessage());
        }

        return musicBandDTO;
    }

    public AlbumDTOList getAlbumDTOFromXml(String xml) {
        AlbumDTOList albumDTOList;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AlbumDTOList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            albumDTOList = (AlbumDTOList) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (JAXBException e) {
            throw new XmlParseException(e.getMessage());
        }
        return albumDTOList;
    }

    public AlbumDTO getSingleAlbumDTOFromXml(String xml) {
        AlbumDTO albumDTO;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AlbumDTO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            albumDTO = (AlbumDTO) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        } catch (JAXBException e) {
            throw new XmlParseException(e.getMessage());
        }
        return albumDTO;
    }
}
