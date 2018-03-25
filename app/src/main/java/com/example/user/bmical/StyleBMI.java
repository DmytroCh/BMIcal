package com.example.user.bmical;

import android.support.constraint.ConstraintLayout;

/**
 * Created by User on 25.03.2018.
 */

public enum StyleBMI {
    BELOW {
        public void format(ConstraintLayout layout){
            layout.setBackgroundResource(R.color.below);
        }
    },
    GOOD {
        public void format(ConstraintLayout layout){
            layout.setBackgroundResource(R.color.good);
        }
    },
    OVER {
        public void format(ConstraintLayout layout){
            layout.setBackgroundResource(R.color.over);
        }
    },
    WRONG {
        public void format(ConstraintLayout layout){
            layout.setBackgroundResource(R.color.wrong);
        }
    };
    public abstract void format(ConstraintLayout layout);
}
