package net.kamradtfamily

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Instant

@RestController
@RequestMapping("/v1/headlines")
class ReadHeadlinesControllerV1(val newsReactiveRepository: InsertsReactiveRepository) {
    val maxLimit = 1000L

    @GetMapping(path = [""], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getFromMongo(@RequestParam(value="from", defaultValue="") from: String,
                     @RequestParam(value="to", defaultValue="") to: String,
                     @RequestParam(value="limit", defaultValue = "1000") limit: Long): Flux<Articles> =
        newsReactiveRepository
            .findAll()
            .flatMap { r -> Flux.fromIterable(r.articles) }
            .filter { r -> filterByDate(Instant.parse(r.publishedAt), from, to) }
            .limitRequest(if (limit > maxLimit) maxLimit else limit)

    fun filterByDate(published: Instant, from: String, to: String) =
        from.isBlank() || published.isAfter(Instant.parse(from))
            && to.isBlank() || published.isBefore(Instant.parse(to))

}