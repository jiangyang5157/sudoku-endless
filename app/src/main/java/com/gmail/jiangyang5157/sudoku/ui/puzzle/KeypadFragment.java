package com.gmail.jiangyang5157.sudoku.ui.puzzle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gmail.jiangyang5157.sudoku.component.BaseFragment;
import com.gmail.jiangyang5157.sudoku.R;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 20:55
 */
public class KeypadFragment extends BaseFragment {

    public static final String FRAGMENT_TAG = "KeypadFragment";

    public interface Listener {
        
        boolean inputNumber(int number);

        boolean onErase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_keypad, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnNumeric1 = (Button) view.findViewById(R.id.btn_numeric_1);
        Button btnNumeric2 = (Button) view.findViewById(R.id.btn_numeric_2);
        Button btnNumeric3 = (Button) view.findViewById(R.id.btn_numeric_3);
        Button btnNumeric4 = (Button) view.findViewById(R.id.btn_numeric_4);
        Button btnNumeric5 = (Button) view.findViewById(R.id.btn_numeric_5);
        Button btnNumeric6 = (Button) view.findViewById(R.id.btn_numeric_6);
        Button btnNumeric7 = (Button) view.findViewById(R.id.btn_numeric_7);
        Button btnNumeric8 = (Button) view.findViewById(R.id.btn_numeric_8);
        Button btnNumeric9 = (Button) view.findViewById(R.id.btn_numeric_9);
        Button btnErase = (Button) view.findViewById(R.id.btn_erase);

        btnNumeric1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof Listener) {
                    ((Listener) getActivity()).inputNumber(1);
                }
            }
        });
        btnNumeric2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof Listener) {
                    ((Listener) getActivity()).inputNumber(2);
                }
            }
        });
        btnNumeric3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof Listener) {
                    ((Listener) getActivity()).inputNumber(3);
                }
            }
        });
        btnNumeric4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof Listener) {
                    ((Listener) getActivity()).inputNumber(4);
                }
            }
        });
        btnNumeric5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof Listener) {
                    ((Listener) getActivity()).inputNumber(5);
                }
            }
        });
        btnNumeric6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof Listener) {
                    ((Listener) getActivity()).inputNumber(6);
                }
            }
        });
        btnNumeric7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof Listener) {
                    ((Listener) getActivity()).inputNumber(7);
                }
            }
        });
        btnNumeric8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof Listener) {
                    ((Listener) getActivity()).inputNumber(8);
                }
            }
        });
        btnNumeric9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof Listener) {
                    ((Listener) getActivity()).inputNumber(9);
                }
            }
        });
        btnErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof Listener) {
                    ((Listener) getActivity()).onErase();
                }
            }
        });
    }
}
