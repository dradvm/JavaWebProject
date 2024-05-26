selectRank();

function selectRank() {
    $(document).ready(function () {
        inputRankID = $('#rank-id').val();
        $('.rank-fee').each(function () {
            if ($(this).attr('rank') === inputRankID) {
                $(this).css({
                   display: 'block' 
                });
            }
            else {
                $(this).css({
                    display: 'none'
                });
            }
        });
        $('.rank-cpo').each(function () {
            if ($(this).attr('rank') === inputRankID) {
                $(this).css({
                   display: 'block' 
                });
            }
            else {
                $(this).css({
                    display: 'none'
                });
            }
        });
        $('.rank-max-dish').each(function () {
            if ($(this).attr('rank') === inputRankID) {
                $(this).css({
                   display: 'block' 
                });
            }
            else {
                $(this).css({
                    display: 'none'
                });
            }
        });
    });
}