package com.nicktoony.scrAI.World.Tasks;

import com.nicktoony.scrAI.Constants;
import com.nicktoony.scrAI.Controllers.RoomController;
import com.nicktoony.scrAI.World.Creeps.CreepWorker;
import com.nicktoony.scrAI.World.Creeps.CreepWrapper;
import com.nicktoony.screeps.Game;
import com.nicktoony.screeps.Spawn;

/**
 * Created by nick on 02/08/15.
 */
public class TaskDepositSpawn extends Task {
    protected Spawn spawn;
    private int storageAvailable = 0;

    public TaskDepositSpawn(RoomController roomController, String associatedId, Spawn spawn) {
        super(roomController, associatedId);
        this.spawn = spawn;
    }

    @Override
    public boolean canAct(CreepWorker creepWorker) {
        return spawn != null
                && (creepWorker.getCreep().carry.energy > 0);
    }

    @Override
    public boolean canAssign(CreepWorker creepWorker) {
        return canAct(creepWorker)
                && (storageAvailable > 0);
    }

    @Override
    public boolean act(CreepWorker creepWorker) {
        if (creepWorker.moveTo(spawn.pos)) {
            creepWorker.getCreep().transferEnergy(spawn);
            return false;
        }
        return true;
    }

    @Override
    public boolean save() {
        if (spawn == null) {
            return false;
        }
        return super.save();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void create() {
        spawn = (Spawn) Game.getObjectById(associatedId);
        if (spawn != null) {
            storageAvailable = spawn.energyCapacity - spawn.energy;
        }
    }

    @Override
    public void assignCreep(CreepWrapper creepWrapper) {
        super.assignCreep(creepWrapper);

        storageAvailable -= creepWrapper.getCreep().carry.energy;
    }

    @Override
    public String getType() {
        return "2";
    }

    @Override
    public int getPriority() {
        return Constants.PRIORITY_DEPOSIT;
    }
}
