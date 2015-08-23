<div class="note mb15">
	<div class="note_body">
		<div class="note_title">Дроплист сервера</div>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($form)?></div>
		<table class="table_info">
			<tr>
				<td width="200px"><label for="SearchForm_mobId">ID моба</label></td>
				<td><input type="text" id="SearchForm_mobId" name="SearchForm[mobId]"></td>
			</tr>
			<tr>
				<td><label for="SearchForm_item_id">ID вещи</label></td>
				<td><input type="text" id="SearchForm_item_id" name="SearchForm[item_id]"></td></td>
			</tr>
			<tr>
				<td><?php $this->widget('CCaptcha', array('showRefreshButton' => false, 'clickableImage' => true)); ?></td>
				<td><?php echo CHtml::activeTextField($form,'captcha') ?></td>
			</tr>
			<tr>
				<td></td>
				<td><input class="button" type="submit" value="Найти" name="yt0"></td>
			</tr>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>

<?php if ($model): ?>
<div class="note">
	<div class="note_body">
		<div class="note_title">Список вещей</div>
		<table class="items">
			<tr>
				<th colspan=2>Название</td>
				<th>ID моба</td>
				<th>Мин.</td>
				<th>Макс.</td>
				<th>Шанс</td>
			</tr>
			<?php foreach ($model as $model) : ?>
			<tr class="center">
				<td width="45px" align="left"><a class="aion-item-icon-large" href="http://aiondatabase.com/item/<?php echo $model->item_id; ?>" target="_blank"></a></td>
				<td align="left"><a class="aion-item-text" href="http://aiondatabase.com/item/<?php echo $model->item_id; ?>"></a></td>
				<td width="80px"><a href="http://aiondatabase.com/npc/<?php echo $model->mobId; ?>"><?php echo $model->mobId; ?></a></td>
				<td width="80px"><?php echo $model->min; ?></td>
				<td width="80px"><?php echo $model->max; ?></td>
				<td width="80px"><?php echo $model->chance; ?></td>
			</tr>
			<?php endforeach ?>
		</table>
	</div>
</div>
<?php endif; ?>