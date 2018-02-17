package net.novapixelnetwork.spaceraiders.ship

import com.boydti.fawe.`object`.schematic.Schematic
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat
import net.novapixelnetwork.spaceraiders.data.DataFolders
import net.novapixelnetwork.spaceraiders.data.DataManager
import net.novapixelnetwork.spaceraiders.world.Planet
import org.bukkit.Location
import java.io.File
import java.util.*

/**
 * Created by owner on 1/5/2018.
 */
class Hangar(val id: Int, val center: Location, val size: Size, val owner: UUID, private val shipID: Int, val autoGenerated: Boolean, val planet: Planet) {

    init {
        size.getSchematic().paste(BukkitWorld(center.world), Vector(center.blockX, center.blockY, center.blockZ), false, true, null)
    }

    val ship: Ship by lazy {
        DataManager.getShip(shipID)!!
    }

    companion object {

        fun createTable(): String{
            return "CREATE TABLE IF NOT EXISTS hangars (" +
                    "id INT AUTO_INCREMENT, " +
                    "x INT NOT NULL, " +
                    "y INT NOT NULL, " +
                    "z INT NOT NULL, " +
                    "planet INT, " +
                    "size VARCHAR(12) NOT NULL, " +
                    "ship INT NOT NULL, " +
                    "auto_generated TINYINT(1) NOT NULL, " +
                    "owner VARCHAR(36) NOT NULL, " +
                    "PRIMARY KEY (`id`));"
        }
    }

    enum class Size(val sizeName: String) {
        SMALL("Scout"), MEDIUM("Fighter"), LARGE("Destroyer");

        override fun toString(): String {
            return sizeName
        }


        fun getSchematic(): Schematic {
            return ClipboardFormat.SCHEMATIC.load(File(DataFolders.hangars, this.name.toLowerCase() + ".schematic"))
        }
    }
}