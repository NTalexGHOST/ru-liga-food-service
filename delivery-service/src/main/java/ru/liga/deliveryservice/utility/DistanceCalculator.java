package ru.liga.deliveryservice.utility;

import lombok.experimental.UtilityClass;
import org.springframework.data.geo.Point;

import java.text.DecimalFormat;

/**
 *  Класс для вычисления расстояния между двумя географическими точками
 *  с использованием формулы Ламберта для длинных линий
 */
@UtilityClass
public class DistanceCalculator {

    private final int earthRadius = 6371;

    /**
     * Метод вычисляет расстояние в км между двумя точками, заданными в формате "(x,y)"
     * @param firstCoords - координаты первой точки
     * @param secondCoords - координаты второй точки
     * @return расстояние в км между точками, округленное до десятых
     */
    public double calculateDistance(String firstCoords, String secondCoords) {

        Point firstPoint = coordsToPoint(firstCoords);
        Point secondPoint = coordsToPoint(secondCoords);

        double latitudeDifference = degreesToRadians(secondPoint.getX() - firstPoint.getX());
        double longitudeDifference = degreesToRadians(secondPoint.getY() - firstPoint.getY());

        double firstLatitude = degreesToRadians(firstPoint.getX());
        double secondLatitude = degreesToRadians(secondPoint.getX());

        double a = Math.pow(Math.sin(latitudeDifference / 2), 2) + Math.pow(Math.sin(longitudeDifference / 2), 2) *
                Math.cos(firstLatitude) * Math.cos(secondLatitude);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        //  Округление результата до #.#
        DecimalFormat roundFormat = new DecimalFormat("#.#");
        double result = Double.parseDouble(roundFormat.format(earthRadius * c));

        return result;
    }

    private Point coordsToPoint(String coordsString) {

        coordsString = coordsString.replaceAll("() ", "");
        String[] coords = coordsString.split(",");

        return new Point(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
    }

    private double degreesToRadians(double degrees) {

        return degrees * Math.PI / 180;
    }
}
