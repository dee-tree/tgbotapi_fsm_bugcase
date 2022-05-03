import dev.inmo.micro_utils.fsm.common.State
import dev.inmo.micro_utils.fsm.common.managers.DefaultStatesManager
import dev.inmo.micro_utils.fsm.common.managers.InMemoryDefaultStatesManagerRepo
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.extensions.behaviour_builder.expectations.waitText
import dev.inmo.tgbotapi.extensions.behaviour_builder.telegramBotWithBehaviourAndFSMAndStartLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand
import dev.inmo.tgbotapi.types.ChatId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


sealed interface CommonState : State
data class AState(override val context: ChatId) : CommonState
data class BState(override val context: ChatId) : CommonState


fun main(args: Array<String>) {
    val botToken = args.first()

    runBlocking {

        val repo = InMemoryDefaultStatesManagerRepo<CommonState>()
        val statesManager = DefaultStatesManager<CommonState>(repo)
        telegramBotWithBehaviourAndFSMAndStartLongPolling<CommonState>(
            botToken,
            statesManager = statesManager,
            scope = CoroutineScope(Dispatchers.IO)
        ) {

            strictlyOn<AState>() { state ->

                sendMessage(state.context, "Hello A")
                val msg = waitText(filter = { it.chat.id == state.context }).first()

                when {
                    msg.text == "go to b" -> BState(state.context)
                    else -> state
                }

            }

            strictlyOn<BState>() {
                sendMessage(it.context, "hello from B")

                it
            }

            // initial filter here: trigger lambda only if the user's state is null
            onCommand("start", initialFilter = { repo.getContextState(it.chat.id) == null }) {
                startChain(AState(it.chat.id))
            }
        }.second.join()
    }

}