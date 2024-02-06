package com.pixelsapphire.wanmin.controller;

import org.jetbrains.annotations.NotNull;

public interface Provider<T> {

    @NotNull T getById(int id);
}
