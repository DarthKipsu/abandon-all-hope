## Abandon all hope

It's apocalypse day 1 and you've made it. You have a group of survivors and a small settlement. 

_Looks like it's safe._ 

### Features

* Zombies. Every day, more zombies.
 * zombies drop weapons, bullets and materials
 * Movement towards nearest player
* Survivors
 * Assign them weapons to fight the zombie threat
 * Build traps and walls to protect your settlement
 * Becomes a zombie upon bite
* Easter egg
 * Type michonne to take the game to the next level

The game is a tower defence inspired survival game. Players need to protect their survivals by killing the every day increasing zombie hordes, or become a zombie themselves. How many days can you survive?

### Users

The game has a single user: the player. User actions include:
 * Selecting and moving players
 * Changing player weapons
 * Building traps
 * Building walls

Fighting zombies is automatic, as well as trap functions and zombie movement.

### Rules of the game / features more precisely

* A new day begins once all zombies have been killed or trapped.
  * Trapped zombies will be cleared at the end of each day.
* Game ends when no survivors are left alive.
* There is a small breathing break between days.
* Amount of zombies increase each day.
  * Most of the zombies arrive from one of three nearby cities (left side of the map), each day produces a horde from one city selected randomly.
  * Some zombies also arrive from other directions.
* Zombies drop loot randomly.
  * Loot is automatically placed in player inventory.
  * Trapped zombie loot is collected at the end of the day.
* Zombies walk towards nearest player, unless trapped.
  * When faced with a wall, it will try to go around by steping to some other direction instead. Zombies are not very smart with walls, they only care about getting towards nearest survivor.
  * The movement will be more precise, the closer the zombie is to it's target.
  * Zombies will try to avoid walking in too tight formations.
* Survivors try to shoot the nearest zombie.
  * Trapped zombies are not seen as enemies and survivors will not attack them (trapped zombies can still attack survivors).
  * Zombie will be removed if a bullet hits them.
  * Survivor can't use a firearm if there's no bullets or the weapon is reloading, in this case melee weapon can be used by melee weapon rules.
  * Bullets are shared between survivors. Each different weapon have their own kinds of bullets.
* Survivors use melee weapon as secondary weapon, if they don't have a firearm, their out of bullets or the firearm is still reloading.
  * Melee weapons have reload times aswell and can only be used after their loaded.
  * Melee weapon can only be used if zombie is inside it's reach.
* Survivors will become a zombie if bitten.
  * All weapons survivor was holding are lost.
* Zombies or survivors can't walk through walls.
  * Survivors can walk through traps unharmed (unless there was a zombie in it, they might get bitten)
* Walls can be built in straight horizonal or vertical lines.
  * Walls can be built on top of each other to strengthen the wall.
  * Walls break when enough zombies hit them.
  * Before breaking, a wall will trun red to notify the player it's about to break.
  * Zombies can't bite through walls, but a survivor might reach through it with melee weapon.
  * Bullets won't hit walls.
* Traps can't be built on top of other traps.
  * Traps have a capacity and once full will let other zombies through.
* Traps and walls can only be built if enough resources.