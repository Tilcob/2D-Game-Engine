package com.game.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.game.component.Animation2D;

import com.badlogic.ashley.core.Entity;
import com.game.component.Attack;
import com.game.component.Fsm;
import com.game.component.Move;

public enum AnimationState implements State<Entity> {
    IDLE {
        @Override
        public void enter(Entity entity) {
            Animation2D.MAPPER.get(entity).setType(Animation2D.AnimationType.IDLE);
        }

        @Override
        public void update(Entity entity) {
            Move move = Move.MAPPER.get(entity);
            if (move != null && !move.isRooted() && !move.getDirection().isZero()) {
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(WALK);
                return;
            }

        }

        @Override
        public void exit(Entity entity) {

        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    },

    WALK {
        @Override
        public void enter(Entity entity) {
            Animation2D.MAPPER.get(entity).setType(Animation2D.AnimationType.WALK);
        }

        @Override
        public void update(Entity entity) {
            Move move = Move.MAPPER.get(entity);
            if (move == null || move.isRooted() || move.getDirection().isZero()) {
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(IDLE);
            }
        }

        @Override
        public void exit(Entity entity) {

        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    },

    ATTACK {
        @Override
        public void enter(Entity entity) {
            Animation2D.MAPPER.get(entity).setType(Animation2D.AnimationType.ATTACK);
        }

        @Override
        public void update(Entity entity) {
            Attack attack = Attack.MAPPER.get(entity);
            if (attack.canAttack()) {
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(IDLE);
            }
        }

        @Override
        public void exit(Entity entity) {
        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    },

    DAMAGED {
        @Override
        public void enter(Entity entity) {
            Animation2D.MAPPER.get(entity).setType(Animation2D.AnimationType.DAMAGED);
        }

        @Override
        public void update(Entity entity) {
            Animation2D animation2D = Animation2D.MAPPER.get(entity);
            if (animation2D.isFinished()) {
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(IDLE);
            }
        }

        @Override
        public void exit(Entity entity) {
        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    }
}
