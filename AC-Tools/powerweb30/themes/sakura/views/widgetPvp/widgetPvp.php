<div class="note mb15">
	<div class="note_body">
		<div class="note_title">Топ PvP</div>
		<table class="items">
			<tr>
				<th>№</th>
				<th>Имя</th>
				<th>Уровень</th>
				<th>PvP</th>
				<th>AP</th>
				<th>Раса</th>
				<th>Класс</th>
				<th>Статус</th>
			</tr>
			<?php $i = 1; foreach ($model as $player): ?>
			<tr class="center">
				<td><?php echo $i++; ?></td>
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$player->name; ?>"><?php echo $player->name; ?></a></td>
				<td><?php echo Info::lvl($player->exp); ?></td>
				<td title="Убийств: За неделю / Всего"><b><?php echo $player->abyssRank->weekly_kill; ?></b> / <?php echo $player->abyssRank->all_kill; ?></td>
				<td><?php echo number_format($player->abyssRank->ap,0,' ',' '); ?></td>
				<td><?php echo Info::race($player->race) ?></td>
				<td><?php echo Info::player_class($player->player_class) ?></td>
				<td><?php echo Info::online($player->online); ?></td>
			</tr>
			<?php endforeach ?>
		</table>
	</div>
</div>