<div class="note mb15">
	<div class="note_body">
		<div class="note_title">Общая информация о игроке <?php echo $player->name; ?></div>
		<table class="items">
			<tr>
				<td rowspan="4" width="112px" align="center"><?php echo Info::face($player->race, $player->gender); ?></td>
				<td width="32px"><?php echo Info::player_class($player->player_class); ?></td>
				<td width="206px"><span class="player_name"><?php echo $player->name; ?></span></td>
				<td width="206px"></td>
				<td width="206px" align="right"><span class="player_name"><?php echo Info::lvl($player->exp); ?> уровень</span></td>
			</tr>
			<tr>
				<td colspan="2"><span>Класс:</span> <?php echo Info::class_text($player->player_class); ?></td>
				<td><span>AP:</span> <?php echo number_format($player['abyssRank']['ap'],0,' ',' '); ?></td>
				<td>HP: <?php echo $player->lifeStat->hp; ?></td>
			</tr>
			<tr>
				<td colspan="2"><span>Раса:</span> <?php echo Info::race_text($player->race); ?></td>
				<td><span>Убийств:</span> <?php echo $player['abyssRank']['all_kill']; ?></td>
				<td>MP: <?php echo $player->lifeStat->mp; ?></td>
			</tr>
		</table>
	</div>
</div>		

<div id="col_left">
<div class="note">
	<div class="note_body">	
		<div class="note_title">Статистика</div>
		<table class="items">
			<tr>
				<td>Статус</td>
				<td><?php echo Info::online($player->online); ?></td>
			</tr>
			<tr>
				<td>Локация</td>
				<td>
					<?php if ($player->show_location == 1): ?>
						<?php echo Info::world($player->world_id); ?>
					<?php else: ?>
						Скрыто
					<?php endif; ?>
				</td>
			</tr>
			<tr>
				<td>Титул</td>
				<td><?php echo Info::title($player->title_id); ?></td>
			</tr>
			<tr>
				<td>Опыт</td>
				<td><?php echo number_format($player->exp,0,' ',' '); ?></td>
				
			</tr>
			<tr>
				<td>AP</td>
				<td><?php echo number_format($player['abyssRank']['ap'],0,' ',' '); ?></td>
			</tr>
			<tr>
				<td>Убийств всего</td>
				<td><?php echo $player['abyssRank']['all_kill']; ?></td>
			</tr>
			<tr>
				<td>Убийств за неделю</td>
				<td><?php echo $player['abyssRank']['weekly_kill']; ?></td>
			</tr>
			<tr>
				<td>Ранг</td>
				<td><?php echo Info::arank($player['abyssRank']['rank']); ?></td>
			</tr>
			<tr>
				<td>Дата создания</td>
				<td><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $player->creation_date); ?></td>
			</tr>
			<tr>
				<td>Последний визит</td>
				<td><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $player->last_online); ?></td>
			</tr>
		</table>
	</div>
</div>
</div>

<div id="col_right">
<div class="note">
	<div class="note_body">
		<div class="note_title">Экипировка</div>
			<?php if ($player->show_inventory == 1): ?>
				<div class="equip">
					<?php foreach ($inventory as $item) echo Equip::image($item->item_id, $item->slot); ?>
				</div>
			<?php else: ?>
				Пользователь скрыл инвентарь
			<?php endif; ?>
		<div class="clear"></div>
	</div>
</div>
</div>