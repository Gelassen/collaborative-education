package ru.home.collaborativeeducation.network.exceptions;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "Отсутсвует соединение с сетью. Проверьте разрешён ли интернет на устройстве";
    }
}
