package com.iissoft.assignment.app.service;

/**
 * Интерфейс для осуществления синхронизации и выгрузки данных
 */
public interface EtlService {
    /**
     * Синхронизация с Базой Данных по заданному файлу
     * @param filename  имя файла откуда будет идти синхронизация
     */
    void performSyncOperation(String filename);

    /**
     * Выгрузка данных с Базы Данных в XML по заданному имени файла
     *
     * @param filename  имя файла куда будет осуществляться выгрузка
     */
    void performDataUploadToXml(String filename);
}
