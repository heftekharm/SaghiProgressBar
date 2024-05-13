package com.hfm.saghi;

import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.LafManagerListener;
import com.intellij.openapi.application.ApplicationActivationListener;
import com.intellij.openapi.wm.IdeFrame;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ApplicationComponent implements LafManagerListener, ApplicationActivationListener {
    @Override
    public void lookAndFeelChanged(@NotNull LafManager source) {
        updateProgressBar();
    }

    @Override
    public void applicationActivated(@NotNull IdeFrame ideFrame) {
        ApplicationActivationListener.super.applicationActivated(ideFrame);
        updateProgressBar();
    }

    private void updateProgressBar(){
        UIManager.put("ProgressBarUI", SaghiProgressBar.class.getName());
        UIManager.getDefaults().put(SaghiProgressBar.class.getName(), SaghiProgressBar.class);
        PoemsRepository.INSTANCE.pickNewPoems();
    }
}
