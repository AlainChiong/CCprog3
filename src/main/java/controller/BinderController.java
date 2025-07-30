package main.java.controller;

import main.java.model.classes.BinderModel;
import main.java.view.BinderView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * BinderController manages a single binder's model and view.
 * It detects selection and notifies a parent controller via ActionListener.
 */
public class BinderController {

    private final BinderModel binderModel;
    private final BinderView binderView;
    private final ActionListener selectionListener;

    public BinderController(BinderModel binderModel, BinderView binderView, ActionListener selectionListener) {
        this.binderModel = binderModel;
        this.binderView = binderView;
        this.selectionListener = selectionListener;
        initViewListeners();
    }

    private void initViewListeners() {
        binderView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectionListener != null) {
                    selectionListener.actionPerformed(
                        new ActionEvent(binderModel, ActionEvent.ACTION_PERFORMED, binderModel.getName())
                    );
                }
            }
        });
    }

    public BinderModel getBinderModel() {
        return binderModel;
    }

    public BinderView getBinderView() {
        return binderView;
    }

    public void setSelected(boolean selected) {
        binderView.setSelected(selected);
    }
}
