<div class="note">
	<div class="note_body">
		<div class="note_title">Топ игроков</div>
		<table class="items">
			<tr>
				<th>Имя</th>
				<th width="95px">Уровень</th>
				<th width="95px">AP</th>
				<th width="95px">Раса</th>
				<th width="95px">Класс</th>
				<th width="95px">Статус</th>
			</tr>
			<?php foreach ($players as $player): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$player->name; ?>"><?php echo $player->name; ?></a></td>
				<td><?php echo Info::lvl($player->exp); ?></td>
				<td><?php echo number_format($player->abyssRank->ap,0,' ',' '); ?></td>
				<td><?php echo Info::race($player->race) ?></td>
				<td><?php echo Info::player_class($player->player_class) ?></td>
				<td><?php echo Info::online($player->online); ?></td>
			</tr>
			<?php endforeach ?>
		</table>
	</div>
</div>

<div class="pages">
	<?php Config::pages($pages); ?>
</div>