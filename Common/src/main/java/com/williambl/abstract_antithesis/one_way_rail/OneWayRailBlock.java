package com.williambl.abstract_antithesis.one_way_rail;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class OneWayRailBlock extends BaseRailBlock {
    public OneWayRailBlock(Properties properties) {
        super(true, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_SOUTH).setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction dir = ctx.getHorizontalDirection();
        return super.getStateForPlacement(ctx).setValue(HORIZONTAL_FACING, dir);
    }

    @Override
    protected BlockState updateDir(Level level, BlockPos pos, BlockState state, boolean alwaysPlace) {
        Direction oldDir = state.getValue(HORIZONTAL_FACING);
        BlockState changed = super.updateDir(level, pos, state, alwaysPlace);
        BlockState updated = changed.setValue(HORIZONTAL_FACING, switch (changed.getValue(RAIL_SHAPE_STRAIGHT)) {
            case NORTH_SOUTH, ASCENDING_NORTH, ASCENDING_SOUTH -> oldDir.getAxis() == Direction.Axis.Z ? oldDir : Direction.NORTH;
            case EAST_WEST, ASCENDING_EAST, ASCENDING_WEST -> oldDir.getAxis() == Direction.Axis.X ? oldDir : Direction.EAST;
            default -> oldDir;
        });

        if (alwaysPlace || updated != changed) {
            level.setBlock(pos, updated, 3);
        }

        return updated;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        Direction direction = state.getValue(HORIZONTAL_FACING);
        if (entity instanceof Minecart && entity.getMotionDirection() != direction) {
            entity.setDeltaMovement(entity.getDeltaMovement().scale(0.5));
        }
    }

    @Override
    public @NotNull Property<RailShape> getShapeProperty() {
        return RAIL_SHAPE_STRAIGHT;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        RailShape shape = state.getValue(RAIL_SHAPE_STRAIGHT);
        return (switch (rotation) {
            case CLOCKWISE_180 -> switch (shape) {
                case NORTH_SOUTH -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_SOUTH);
                case EAST_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.EAST_WEST);
                case ASCENDING_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_WEST);
                case ASCENDING_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_EAST);
                case ASCENDING_NORTH -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_SOUTH);
                case ASCENDING_SOUTH -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_NORTH);
                case SOUTH_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_WEST);
                case SOUTH_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_EAST);
                case NORTH_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.SOUTH_EAST);
                case NORTH_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.SOUTH_WEST);
            };
            case COUNTERCLOCKWISE_90 -> switch (shape) {
                case NORTH_SOUTH -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.EAST_WEST);
                case EAST_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_SOUTH);
                case ASCENDING_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_NORTH);
                case ASCENDING_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_SOUTH);
                case ASCENDING_NORTH -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_WEST);
                case ASCENDING_SOUTH -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_EAST);
                case SOUTH_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_EAST);
                case SOUTH_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.SOUTH_EAST);
                case NORTH_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.SOUTH_WEST);
                case NORTH_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_WEST);
            };
            case CLOCKWISE_90 -> switch (shape) {
                case NORTH_SOUTH -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.EAST_WEST);
                case EAST_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_SOUTH);
                case ASCENDING_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_SOUTH);
                case ASCENDING_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_NORTH);
                case ASCENDING_NORTH -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_EAST);
                case ASCENDING_SOUTH -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_WEST);
                case SOUTH_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.SOUTH_WEST);
                case SOUTH_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_WEST);
                case NORTH_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_EAST);
                case NORTH_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.SOUTH_EAST);
            };
            default -> state;
        }).setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        RailShape shape = state.getValue(RAIL_SHAPE_STRAIGHT);
        return (switch (mirror) {
            case LEFT_RIGHT -> switch (shape) {
                    case ASCENDING_NORTH -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_EAST);
                    case SOUTH_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_WEST);
                    case NORTH_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.SOUTH_WEST);
                    case NORTH_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.SOUTH_EAST);
                    default -> super.mirror(state, mirror);
                };
            case FRONT_BACK -> switch (shape) {
                    case ASCENDING_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.ASCENDING_EAST);
                    case SOUTH_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.SOUTH_WEST);
                    case SOUTH_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.SOUTH_EAST);
                    case NORTH_WEST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_EAST);
                    case NORTH_EAST -> state.setValue(RAIL_SHAPE_STRAIGHT, RailShape.NORTH_WEST);
                    default -> super.mirror(state, mirror);
                };
            default -> super.mirror(state, mirror);
        }).setValue(HORIZONTAL_FACING, mirror.mirror(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RAIL_SHAPE_STRAIGHT, HORIZONTAL_FACING, WATERLOGGED);
    }
}
