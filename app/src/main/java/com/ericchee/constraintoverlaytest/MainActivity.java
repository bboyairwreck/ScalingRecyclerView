package com.ericchee.constraintoverlaytest;

import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.expandedRecycler)
    RecyclerView expandedRecycler;

    @BindView(R.id.collapsedRecycler)
    RecyclerView collapsedRecylcler;

    @BindView(R.id.toggleButton)
    TextView toggleButton;

    private static final int DURATION = 500;
    private boolean isExpanded = false;

    private class AlphaAnimationListener implements Animator.AnimatorListener {
        private final View view;
        private final float alpha;

        AlphaAnimationListener(View view, float alpha) {
            this.view = view;
            this.alpha = alpha;
        }

        @Override
        public void onAnimationStart(Animator animator) {}

        @Override
        public void onAnimationEnd(Animator animator) {
            view.setAlpha(alpha);
        }

        @Override
        public void onAnimationCancel(Animator animator) {}

        @Override
        public void onAnimationRepeat(Animator animator) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final ExpandedAdapter expandedAdapter = new ExpandedAdapter();
        expandedRecycler.setAdapter(expandedAdapter);
        expandedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        final CollapsedAdapter collapsedAdapter = new CollapsedAdapter();
        collapsedRecylcler.setAdapter(collapsedAdapter);
        collapsedRecylcler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int expandedHeight = expandedRecycler.getMeasuredHeight();
                int expandedWidth = expandedRecycler.getMeasuredWidth();
                int collapsedHeight = collapsedRecylcler.getMeasuredHeight();
                int collapsedWidth = collapsedRecylcler.getMeasuredWidth();

                float expandFactor = expandedHeight / (float) collapsedHeight;
                float collapseFactor = collapsedHeight / (float) expandedHeight;

                if (!isExpanded) {
                    collapsedRecylcler.setPivotX(collapsedWidth/2f);
                    collapsedRecylcler.setPivotY(collapsedHeight);
                    expandedRecycler.setScaleX(1f);
                    expandedRecycler.setScaleY(1f);
                    collapsedRecylcler.animate().scaleX(expandFactor).scaleY(expandFactor).setDuration(DURATION).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {}

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            collapsedRecylcler.animate().alpha(0f).setDuration(DURATION).setListener(new AlphaAnimationListener(collapsedRecylcler, 0f));
                            expandedRecycler.animate().alpha(1f).setDuration(DURATION).setListener(new AlphaAnimationListener(expandedRecycler, 1f));
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {}

                        @Override
                        public void onAnimationRepeat(Animator animator) {}
                    });
                } else {
                    expandedRecycler.setPivotX(expandedWidth/2f);
                    expandedRecycler.setPivotY(expandedHeight);
                    collapsedRecylcler.setScaleX(1f);
                    collapsedRecylcler.setScaleY(1f);
                    expandedRecycler.animate().scaleX(collapseFactor).scaleY(collapseFactor).setDuration(DURATION).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) { }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            expandedRecycler.animate().alpha(0).setDuration(DURATION).setListener(new AlphaAnimationListener(expandedRecycler, 0f));
                            collapsedRecylcler.animate().alpha(1f).setDuration(DURATION).setListener(new AlphaAnimationListener(collapsedRecylcler, 1f));
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {}

                        @Override
                        public void onAnimationRepeat(Animator animator) {}
                    });
                }
                isExpanded = !isExpanded;
            }
        });
    }
}
