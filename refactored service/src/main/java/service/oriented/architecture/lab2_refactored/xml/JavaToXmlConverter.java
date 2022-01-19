package service.oriented.architecture.lab2_refactored.xml;


import service.oriented.architecture.lab2_refactored.dto.CountDTO;
import service.oriented.architecture.lab2_refactored.dto.ExceptionDTO;
import service.oriented.architecture.lab2_refactored.dto.dtoList.AlbumDTOList;
import service.oriented.architecture.lab2_refactored.dto.dtoList.CoordinatesDTOList;
import service.oriented.architecture.lab2_refactored.dto.dtoList.MusicBandDTOList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class JavaToXmlConverter {
    public String coordinatesToXML(CoordinatesDTOList coordinateDTO) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CoordinatesDTOList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(coordinateDTO, sw);
            return sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String musicBandsToXML(MusicBandDTOList band) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MusicBandDTOList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(band, sw);
            return sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String albumsToXML(AlbumDTOList album) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AlbumDTOList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(album, sw);
            return sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String countToXML(CountDTO employee) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CountDTO.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(employee, sw);
            return sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String exceprionToXML(ExceptionDTO exception) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ExceptionDTO.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(exception, sw);
            return sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }
}
