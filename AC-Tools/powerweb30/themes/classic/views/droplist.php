






<div class="note mb15">
	<div class="note_title">
		Server droplist
	</div>
	<div class="note_body">
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($form)?></div>
		<table class="table_info">
			<tr>
				<td width="200px"><label for="SearchForm_mobId">Monster ID</label></td>
				<td><input type="text" id="SearchForm_mobId" name="SearchForm[mobId]"></td>
			</tr>
			<tr>
				<td><label for="SearchForm_item_id">Item ID</label></td>
				<td><input type="text" id="SearchForm_item_id" name="SearchForm[item_id]"></td></td>
			</tr>
			<tr>
				<td><?php $this->widget('CCaptcha', array('showRefreshButton' => false, 'clickableImage' => true)); ?></td>
				<td><?php echo CHtml::activeTextField($form,'captcha') ?></td>
			</tr>
			<tr>
				<td></td>
				<td><input class="button" type="submit" value="Search" name="yt0"></td>
			</tr>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>


<?php if ($model): ?>
<div class="note">
	<div class="note_title">
		List
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th colspan=2>Title</td>
				<th>Monster ID</td>
				<th>Min.</td>
				<th>Max.</td>
				<th>Chance</td>
			</tr>
			<?php foreach ($model as $model) : ?>
			<tr class="center">
				<td width="45px" align="left"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/<?php echo $model->item_id; ?>" target="_blank"></a></td>
				<td align="left"><a class="aion-item-text" href="http://www.aiondatabase.com/item/<?php echo $model->item_id; ?>"></a></td>
				<td width="80px"><a href="http://www.aiondatabase.com/npc/<?php echo $model->mobId; ?>"><?php echo $model->mobId; ?></a></td>
				<td width="80px"><?php echo $model->min; ?></td>
				<td width="80px"><?php echo $model->max; ?></td>
				<td width="80px"><?php echo $model->chance; ?></td>
			</tr>
			<?php endforeach ?>
		</table>
	</div>
</div>
<?php endif; ?>