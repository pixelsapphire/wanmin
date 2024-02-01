package com.pixelsapphire.wanmin.controller;

import org.jetbrains.annotations.NotNull;

public interface Provider<T> {

    @NotNull <K> T getByKey(K id);
}
