package com.music.fmv.core.managers;

import com.music.fmv.core.Core;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:58 PM
 */
public abstract class Manager {
    protected Core core;
    protected Manager(Core coreManager){
        core = coreManager;
    }

    protected abstract void finish();
}
