<div class="note">
	<div class="note_body">
		<div class="note_title">Информация</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
	</div>
</div>