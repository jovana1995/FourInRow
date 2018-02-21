package com.example.aleksandra.a4inrow.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleksandra.a4inrow.R;
import com.example.aleksandra.a4inrow.presenters.GamePresenter;
import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.utils.SharedPreferencesUtils;
import com.example.aleksandra.a4inrow.views.IGameView;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Aleksandra on 02/12/2017.
 */

@SuppressWarnings({"DefaultFileTemplate", "FieldCanBeLocal"})
public class GameActivity extends BaseActivity<IGameView, GamePresenter> implements IGameView {

    private ImageView[][] cells;
    private View boardView;
    private Board board;
    private ViewHolder viewHolder;
    private static int NUM_ROWS = 6;
    private static int NUM_COLS = 7;
    private static int isSet = 0;
    private Button startButton;

    private class ViewHolder {
        TextView winnerText;
        ImageView turnIndicatorImageView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        reset();
        try {
            isSet = 0;
            getPresenter().startGame();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        startButton.setVisibility(View.VISIBLE);
        boardView.setOnTouchListener(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initViews();
        isSet = 0;
    }

    private void initViews() {
        board = new Board(NUM_COLS, NUM_ROWS);
        boardView = findViewById(R.id.game_board);
        buildCells();
        startButton = (Button) findViewById(R.id.reset_button);
        startButton.setOnClickListener(view -> {
            try {
                if(isSet == 0){
                    showToast("Not found other player yet. Please try again in a little while.");
                } else
                if (isSet == 2) {
                    getPresenter().receiveData();
                    startButton.setVisibility(View.INVISIBLE);
                    setOnToucListener();
                    isSet = 0;
                } else
                if (isSet == 1) {
                    showToast("You are playing first. Make a move.");
                    startButton.setVisibility(View.INVISIBLE);
                    setOnToucListener();
                    isSet = 0;
                }
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        });
        startButton.setVisibility(View.VISIBLE);
        viewHolder = new ViewHolder();
        viewHolder.turnIndicatorImageView = (ImageView) findViewById(R.id.turn_indicator_image_view);
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
        viewHolder.winnerText = (TextView) findViewById(R.id.winner_text);
        viewHolder.winnerText.setVisibility(View.GONE);
    }

    private void setOnToucListener(){
        boardView.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_UP: {
                    int col = colAtX(motionEvent.getX());
                    if (col != -1)
                        try {
                            drop(col);
                            String s = "";
                            s += col + " ";
                            s += SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID);
                            if (board.checkForWin()) {
                                getPresenter().sendData(s);
                                win();
                            } else {
                                getPresenter().sendData(s);
                                getPresenter().receiveData();
                            }
                        } catch (IOException | TimeoutException e) {
                            e.printStackTrace();
                        }
                }
            }
            return true;
        });
    }

    private void buildCells() {
        cells = new ImageView[NUM_ROWS][NUM_COLS];
        for (int r = 0; r < NUM_ROWS; r++) {
            ViewGroup row = (ViewGroup) ((ViewGroup) boardView).getChildAt(r);
            row.setClipChildren(false);
            for (int c = 0; c < NUM_COLS; c++) {
                ImageView imageView = (ImageView) row.getChildAt(c);
                imageView.setImageResource(android.R.color.transparent);
                cells[r][c] = imageView;
            }
        }
    }

    public void drop(int col) throws IOException, TimeoutException {
        if (board.hasWinner)
            return;
        int row = board.lastAvailableRow(col);
        if (row == -1)
            return;
        final ImageView cell = cells[row][col];
        float move = -(cell.getHeight() * row + cell.getHeight() + 15);
        cell.setY(move);
        cell.setImageResource(resourceForTurn());
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, Math.abs(move));
        anim.setDuration(850);
        anim.setFillAfter(true);
        cell.startAnimation(anim);
        board.drop(col, row);
    }

    private void win() {
        int color = board.turn == Board.Turn.FIRST ? ContextCompat.getColor(mContext, R.color.primary_player)
                : ContextCompat.getColor(mContext, R.color.secondary_player);
        viewHolder.winnerText.setTextColor(color);
        viewHolder.winnerText.setVisibility(View.VISIBLE);
    }

    private void lose() {
        int color = board.turn == Board.Turn.FIRST ? ContextCompat.getColor(mContext, R.color.primary_player)
                : ContextCompat.getColor(mContext, R.color.secondary_player);
        viewHolder.winnerText.setTextColor(color);
        viewHolder.winnerText.setText(R.string.lose);
        viewHolder.winnerText.setVisibility(View.VISIBLE);
    }

    private void changeTurn() {
        board.toggleTurn();
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
    }

    private int colAtX(float x) {
        float colWidth = cells[0][0].getWidth();
        int col = (int) x / (int) colWidth;
        if (col < 0 || col > 6)
            return -1;
        return col;
    }

    private int resourceForTurn() {
        switch (board.turn) {
            case FIRST:
                return R.drawable.red;
            case SECOND:
                return R.drawable.yellow;
        }
        return R.drawable.red;
    }

    private void reset() {
        board.reset();
        viewHolder.winnerText.setVisibility(View.GONE);
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
        for (int r = 0; r < NUM_ROWS; r++) {
            for (int c = 0; c < NUM_COLS; c++) {
                cells[r][c].setImageResource(android.R.color.transparent);
            }
        }
    }

    @NonNull
    @Override
    public GamePresenter createPresenter() {
        return new GamePresenter();
    }

    @Override
    public void showData(int col) throws IOException, TimeoutException {
        changeTurn();
        drop(col);
        changeTurn();
        if (board.checkForWinOtherPlayer()) {
            changeTurn();
            lose();
        }
    }

    @Override
    public void showDataAndWait() throws IOException, TimeoutException {
        getPresenter().receiveData();
    }

    @Override
    public void finishSending() throws IOException, TimeoutException {
        getPresenter().receiveData();
    }

    @Override
    public void start(int i) throws IOException, TimeoutException {
        if (i == SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)) {
            board.setTurn(Board.Turn.FIRST);
            isSet = 1;
        } else {
            board.setTurn(Board.Turn.SECOND);
            isSet = 2;
        }
    }

    @Override
    public void otherPlayerHasGone() {
        boardView.setOnTouchListener(null);
        showToast("We are sorry. Other player has left the game.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    getPresenter().sendData("gone");
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
                break;
        }
        onBackPressed();
        return true;
    }
}
